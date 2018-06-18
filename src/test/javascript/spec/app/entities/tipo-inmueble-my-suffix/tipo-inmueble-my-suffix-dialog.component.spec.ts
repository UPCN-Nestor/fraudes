/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { TipoInmuebleMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix-dialog.component';
import { TipoInmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.service';
import { TipoInmuebleMySuffix } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.model';

describe('Component Tests', () => {

    describe('TipoInmuebleMySuffix Management Dialog Component', () => {
        let comp: TipoInmuebleMySuffixDialogComponent;
        let fixture: ComponentFixture<TipoInmuebleMySuffixDialogComponent>;
        let service: TipoInmuebleMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [TipoInmuebleMySuffixDialogComponent],
                providers: [
                    TipoInmuebleMySuffixService
                ]
            })
            .overrideTemplate(TipoInmuebleMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoInmuebleMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoInmuebleMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TipoInmuebleMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.tipoInmueble = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tipoInmuebleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TipoInmuebleMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.tipoInmueble = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tipoInmuebleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
