/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { InspeccionMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix/inspeccion-my-suffix-dialog.component';
import { InspeccionMySuffixService } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix/inspeccion-my-suffix.service';
import { InspeccionMySuffix } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix/inspeccion-my-suffix.model';
import { AnomaliaMySuffixService } from '../../../../../../main/webapp/app/entities/anomalia-my-suffix';
import { TrabajoMySuffixService } from '../../../../../../main/webapp/app/entities/trabajo-my-suffix';
import { InmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/inmueble-my-suffix';
import { EtapaMySuffixService } from '../../../../../../main/webapp/app/entities/etapa-my-suffix';
import { EstadoMySuffixService } from '../../../../../../main/webapp/app/entities/estado-my-suffix';
import { TipoInmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix';

describe('Component Tests', () => {

    describe('InspeccionMySuffix Management Dialog Component', () => {
        let comp: InspeccionMySuffixDialogComponent;
        let fixture: ComponentFixture<InspeccionMySuffixDialogComponent>;
        let service: InspeccionMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [InspeccionMySuffixDialogComponent],
                providers: [
                    AnomaliaMySuffixService,
                    TrabajoMySuffixService,
                    InmuebleMySuffixService,
                    EtapaMySuffixService,
                    EstadoMySuffixService,
                    TipoInmuebleMySuffixService,
                    InspeccionMySuffixService
                ]
            })
            .overrideTemplate(InspeccionMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InspeccionMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InspeccionMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InspeccionMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.inspeccion = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'inspeccionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InspeccionMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.inspeccion = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'inspeccionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
