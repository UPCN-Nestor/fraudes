/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { AnomaliaMedidorMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix-dialog.component';
import { AnomaliaMedidorMySuffixService } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix.service';
import { AnomaliaMedidorMySuffix } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix.model';
import { InspeccionMySuffixService } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix';

describe('Component Tests', () => {

    describe('AnomaliaMedidorMySuffix Management Dialog Component', () => {
        let comp: AnomaliaMedidorMySuffixDialogComponent;
        let fixture: ComponentFixture<AnomaliaMedidorMySuffixDialogComponent>;
        let service: AnomaliaMedidorMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [AnomaliaMedidorMySuffixDialogComponent],
                providers: [
                    InspeccionMySuffixService,
                    AnomaliaMedidorMySuffixService
                ]
            })
            .overrideTemplate(AnomaliaMedidorMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnomaliaMedidorMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnomaliaMedidorMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AnomaliaMedidorMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.anomaliaMedidor = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'anomaliaMedidorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AnomaliaMedidorMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.anomaliaMedidor = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'anomaliaMedidorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
